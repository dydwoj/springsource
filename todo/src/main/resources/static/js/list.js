// checkbox 클릭시, checkbox value 가져오기
// "data-id=${dto.id}" 값 가져오기

// 이벤트 버블링 이용
document.querySelector(".list-group").addEventListener("click", (e) => {
  // 어떤 label 안 checkbox 에서 이벤트가 발생했는지 확인
  const chk = e.target;
  // checkbox 체크, 해제 여부 확인
  console.log(chk.checked);

  // id 값 가져오기
  // closest("선택자") : 부모에서 제일 가까운 요소 찾기
  const id = chk.closest("label").dataset.id;
  console.log(id);

  // actionForm 찾은 후 요소들의 value 값 변경하기
  const actionForm = document.querySelector("#actionForm");
  actionForm.id.value = id;
  actionForm.completed.value = chk.checked;

  actionForm.submit();
});
