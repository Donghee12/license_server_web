<!-- 페이지에 대한 콘텐츠 -->
// 페이지 로드 시 처음에는 목차 1을 보이도록 설정
document.getElementById('content-1').style.display = 'block';

// 목차 항목을 클릭하면 해당 콘텐츠를 보여주는 함수
function showContent(contentId) {
  // 모든 콘텐츠 숨김
  document.querySelectorAll('.content > div').forEach(function(content) {
    content.style.display = 'none';
  });

  // 선택한 콘텐츠만 보여줌
  document.getElementById('content-' + contentId).style.display = 'block';
}


// 하위 목차를 보이거나 숨기는 함수
function toggleSubChapters() {
  var subChapters = document.getElementById("sub-chapters");
  if (subChapters.style.display === "none" || subChapters.style.display === "") {
    subChapters.style.display = "block";
  } else {
    subChapters.style.display = "none";
  }
}

// 하위 목차를 클릭하면 해당 내용을 오른쪽 콘텐츠에 표시하는 함수
function showSubContent(subChapterNumber) {
  // 숨겨져 있던 모든 하위 목차 콘텐츠를 숨김 처리
  var subContents = document.querySelectorAll(".sub-content");
  subContents.forEach(function (subContent) {
    subContent.style.display = "none";
  });

  // 클릭한 하위 목차 콘텐츠만 보이도록 설정
  var subContentId = "sub-content-" + subChapterNumber;
  var subContent = document.getElementById(subContentId);
  if (subContent) {
    subContent.style.display = "block";
  }
}

// 초기에는 모든 하위 목차 콘텐츠를 숨김 처리
var subContents = document.querySelectorAll(".sub-content");
subContents.forEach(function (subContent) {
  subContent.style.display = "none";
});

//---------------------------------------------------------------------------------------------//
