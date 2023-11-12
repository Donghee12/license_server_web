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
toggleSubChapters('sub-chapters3');
toggleSubChapters('sub-chapters4');

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

  //스크롤 위치 초기화
  window.scrollTo(0, 0);


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


// 라이센스 리스트 버튼 클릭 시 이벤트 핸들러
document.getElementById('license-list-button').addEventListener('click', function () {
  // License List 목차로 이동
  showContent(3);
});

// 클라이언트 수 버튼 클릭 시 이벤트 핸들러 (예시)
document.getElementById('client-count-button').addEventListener('click', function () {
  // 클라이언트 수 목차로 이동
  showContent(2); // 이 숫자는 목차에 따라 변경해야 합니다.
});



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

/*-------------------------------------------------------------------------------------------------------------*/


$(document).ready(function() {
  $(".delete-form").submit(function(e) {
    // 폼 전송 막기
    e.preventDefault();

    // 라이선스 ID 가져오기
    var licenseId = $(this).data("licenseId");

    // CSRF 토큰 가져오기 (JSP에서 사용한 경우)
    var csrfToken = $("input[name='_csrf']").val();

    // 또는 직접 설정한 경우
    // var csrfToken = "your_csrf_token_value";

    // AJAX 요청 보내기
    $.ajax({
      type: "POST",
      url: "/delete-license",
      data: { licenseId: licenseId },
      beforeSend: function(xhr) {
        // CSRF 토큰을 헤더에 추가
        xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
      },
      success: function(response) {
        // 성공적으로 처리된 경우
        console.log(response);
        // 여기에서 필요한 화면 업데이트 등을 수행할 수 있습니다.
      },
      error: function(error) {
        // 오류가 발생한 경우
        console.log(error);
      }
    });
  });
});

