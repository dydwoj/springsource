package com.example.novels.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.novels.dto.NovelDTO;
import com.example.novels.dto.PageRequestDTO;
import com.example.novels.dto.PageResultDTO;
import com.example.novels.entity.Genre;
import com.example.novels.entity.Novel;
import com.example.novels.repository.GradeRepository;
import com.example.novels.repository.NovelRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class NovelService {

    private final NovelRepository novelRepository;

    private final GradeRepository gradeRepository;

    public PageResultDTO<NovelDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("id").descending());
        Page<Object[]> result = novelRepository.list(pageable,
                pageRequestDTO.getGenre(), pageRequestDTO.getKeyword());

        // entity => dto
        List<NovelDTO> dtoList = result.get().map(arr -> {
            Novel novel = (Novel) arr[0];
            Genre genre = (Genre) arr[1];
            Double ratingAvg = (Double) arr[2];

            NovelDTO novelDTO = NovelDTO.builder()
                    .id(novel.getId())
                    .title(novel.getTitle())
                    .author(novel.getAuthor())
                    .publishedDate(novel.getPublishedDate())
                    .available(novel.isAvailable())
                    .gid(genre.getId())
                    .genreName(genre.getName())
                    .rating(ratingAvg != null ? ratingAvg.intValue() : 0)
                    .build();
            return novelDTO;
        }).collect(Collectors.toList());
        long totalCnt = result.getTotalElements();
        return PageResultDTO.<NovelDTO>withAll().dtoList(dtoList).totalCount(totalCnt).pageRequestDTO(pageRequestDTO)
                .build();
    }

    public NovelDTO getRow(Long id) {
        Object[] result = novelRepository.getNovelByID(id);
        Novel novel = (Novel) result[0];
        Genre genre = (Genre) result[1];
        Double ratingAvg = (Double) result[2];
        return entityToDto(novel, genre, ratingAvg);
    }

    public Long avaUpdate(NovelDTO novelDTO) {
        Novel novel = novelRepository.findById(novelDTO.getId()).get();
        novel.changeAvailable(novelDTO.isAvailable());
        return novelRepository.save(novel).getId();
    }

    public Long pubUpdate(NovelDTO novelDTO) {
        // publishedDate 변경
        Novel novel = novelRepository.findById(novelDTO.getId()).get();
        novel.changePublishedDate(novelDTO.getPublishedDate());
        return novelRepository.save(novel).getId();
    }

    public void novelRemove(Long id) {
        Novel novel = Novel.builder().id(id).build();

        gradeRepository.deleteByNovel(novel);
        novelRepository.delete(novel);
    }

    public Long novelInsert(NovelDTO novelDTO) {
        Novel novel = Novel.builder()
                .title(novelDTO.getTitle())
                .author(novelDTO.getAuthor())
                .publishedDate(novelDTO.getPublishedDate())
                .available(novelDTO.isAvailable())
                .genre(Genre.builder().id(novelDTO.getGid()).build())
                .build();
        return novelRepository.save(novel).getId();
    }

    private NovelDTO entityToDto(Novel novel, Genre genre, Double ratingAvg) {
        NovelDTO novelDTO = NovelDTO.builder()
                .id(novel.getId())
                .title(novel.getTitle())
                .author(novel.getAuthor())
                .publishedDate(novel.getPublishedDate())
                .available(novel.isAvailable())
                .gid(genre.getId())
                .genreName(genre.getName())
                .rating(ratingAvg != null ? ratingAvg.intValue() : 0)
                .build();
        return novelDTO;
    }

}
