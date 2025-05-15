const showUploadImages = (arr) => {
  const output = document.querySelector("#output");
  arr.forEach((element) => {
    output.insertAdjacentHTML("beforeend", `<img src="/upload/display?fileName=${element.imageURL}">`);
  });
};

document.querySelector("button").addEventListener("click", () => {
  // 버튼 클릭 시 uploadFiles 가져오기
  const inputFile = document.querySelector('[name="uploadFiles"]');
  console.log(inputFile);
  const files = inputFile.files;
  console.log(files);

  // form 생성 후 업로드 된 파일 append
  let form = new FormData();

  for (let i = 0; i < files.length; i++) {
    form.append("uploadFiles", files[i]);
  }
  axios
    .post(`/upload/files`, form, {
      headers: {
        "X-CSRF-TOKEN": csrf,
      },
    })
    .then((res) => {
      console.log(res.data);
      showUploadImages(res.data);
    });
});
