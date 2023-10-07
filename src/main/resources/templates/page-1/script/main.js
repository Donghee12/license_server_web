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

  //스크롤 위치 초기화
  window.scrollTo(0, 0);

}


// 하위 목차를 보이거나 숨기는 함수
function toggleSubChapters(subChaptersId) {
  var subChapters = document.getElementById(subChaptersId);
  if (subChapters.style.display === "block" || subChapters.style.display === "") {
    subChapters.style.display = "none";
  } else {
    subChapters.style.display = "block";
  }
}

// 페이지 로드 시 처음에는 하위 목차를 숨김
toggleSubChapters('sub-chapters1');
toggleSubChapters('sub-chapters2');


// 하위 목차를 클릭하면 해당 내용을 오른쪽 콘텐츠에 표시하는 함수
function showSubContent(subChapterNumber, parentChapterName, subChapterName) {
  // 숨겨져 있던 모든 하위 목차 콘텐츠를 숨김 처리
  var subContents = document.querySelectorAll(".sub-content");
  subContents.forEach(function (subContent) {
    subContent.style.display = "none";
  });

  document.querySelectorAll('.content > div').forEach(function(content) {
    content.style.display = 'none';
  });


  // 클릭한 하위 목차 콘텐츠만 보이도록 설정
  var subContentId = "sub-content-" + subChapterNumber;
  var subContent = document.getElementById(subContentId);
  if (subContent) {
    subContent.style.display = "block";
  }

  // .small-content의 h2에 하위 목차 정보 표시
  var smallContentTitle = document.getElementById('small-content-title');
  var contentTitle = parentChapterName + ' > ' + subChapterName;
  smallContentTitle.textContent = contentTitle;

}

//---------------------------------------------------------------------------------------------//

// 목차 클릭 이벤트에 따라 .small-content의 h2 내용을 변경하는 함수
function updateSmallContentTitle(title) {
  var smallContentTitle = document.getElementById('small-content-title');

  // 대제목 1과 대제목 2를 클릭한 경우에만 업데이트
  if (title !== '대제목 1' && title !== '대제목 2') {
    smallContentTitle.textContent = title;
  }
}

// 목차 클릭 이벤트 설정
document.querySelectorAll('.sidebar-item').forEach(function (item) {
  item.addEventListener('click', function () {
    var title = item.textContent;
    updateSmallContentTitle(title);
  });
});