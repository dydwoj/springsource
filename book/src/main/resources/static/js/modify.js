// 삭제 버튼이 클릭이 되면 actionFrom submit
document.querySelector(".btn-danger").addEventListener("click", () => {
  const actionForm = document.querySelector("#actionForm");
  actionForm.submit();
});
