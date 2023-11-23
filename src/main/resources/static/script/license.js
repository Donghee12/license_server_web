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
    var selectedContent =document.getElementById('content-' + contentId);
    selectedContent.style.display = 'block';

    //스크롤 위치 초기화
    window.scrollTo(0, 0);

    // 추가: DashBoard를 클릭했을 때 admin 페이지로 이동
    if (contentId === 1) {
        window.location.href = '/admin';
    }

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
function showLicenseList() {
    // License List 목차로 이동
    showSubContent(3);
    var smallContentTitle = document.getElementById('small-content-title');
    smallContentTitle.textContent = 'License > License List';

}

// 클라이언트 수 버튼 클릭 시 이벤트 핸들러 (예시)
document.getElementById('client-count-button').addEventListener('click', function () {
    // 클라이언트 수 목차로 이동
    showSubContent(6);
    var smallContentTitle = document.getElementById('small-content-title');
    smallContentTitle.textContent = 'Clients > View All Clients';
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
    $(".delete-form").submit(function(event) {
        event.preventDefault();


        var licenseKey = $(this).attr("data-licenseId");
        var confirmed = confirm("정말로 삭제하시겠습니까?");

        if (confirmed) {
            // 삭제 로직 수행
            $.ajax({
                type: "DELETE",
                url: "/api/licenses/" +licenseKey,

                success: function(response) {
                    console.log(response);
                    // 삭제 성공 후의 처리
                    window.location.reload();
                },
                error: function(error) {
                    console.log(error);
                    // 오류 발생 시의 처리
                }
            });
        }
    });

});

/*-----------------------------------------------------------------------------------------*/


//라이센스 검색창

function filterLicenseTable() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("license-search");
    filter = input.value.toUpperCase();
    table = document.querySelector(".license-table");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

function handleSearchKeyPress(event) {
    if (event.key === "Enter") {
        filterLicenseTable();
    }
}



/*-------------------------------------------------------------------------------------------------------------*/

// 페이지 로드 시 초기 표시되는 라이센스 리스트 개수
var licensesPerPage = 6;

// 현재 페이지와 전체 페이지 수를 저장하는 변수
var currentPage = 1;
var totalPage;

// 라이센스 리스트 테이블의 모든 행을 숨기는 함수
function hideAllRows() {
    var rows = document.querySelectorAll('.license-table tbody tr');
    rows.forEach(function (row) {
        row.style.display = 'none';
    });
}

// 현재 페이지에 해당하는 행만 보이도록 하는 함수
function showRowsForCurrentPage() {
    hideAllRows();

    var startIndex = (currentPage - 1) * licensesPerPage;
    var endIndex = startIndex + licensesPerPage;
    var rows = document.querySelectorAll('.license-table tbody tr');

    for (var i = startIndex; i < endIndex && i < rows.length; i++) {
        rows[i].style.display = '';
    }
}

// 이전 페이지로 이동하는 함수
function goToPrevPage() {
    if (currentPage > 1) {
        currentPage--;
        showRowsForCurrentPage();
    }
}

// 다음 페이지로 이동하는 함수
function goToNextPage(event) {
    if (currentPage < totalPage) {
        currentPage++;
        showRowsForCurrentPage();
    }

    // 페이지 이동을 막기 위해 이벤트의 기본 동작을 막음
    if (event) {
        event.preventDefault();
    }
}

// 페이지 로드 시 초기 설정
document.addEventListener('DOMContentLoaded', function () {
    var rows = document.querySelectorAll('.license-table tbody tr');
    totalPage = Math.ceil(rows.length / licensesPerPage);

    // 페이지 로드 시 처음에는 첫 페이지의 행들만 보이도록 설정
    showRowsForCurrentPage();

    // 초기 화면을 라이센스 리스트로 설정
    showLicenseList();
});

// 이전 버튼 클릭 시 이벤트 핸들러
document.querySelector('.prev-button').addEventListener('click', function () {
    goToPrevPage();
});

// 다음 버튼 클릭 시 이벤트 핸들러
document.querySelector('.next-button').addEventListener('click', function () {
    goToNextPage();
});

/*----------------------------------------------------------------------------------------------------*/

document.querySelector('#sub-chapters4 li:nth-child(1)').addEventListener('click', function () {
    location.href = '/admin/user_info';
});
document.querySelector('#sub-chapters4 li:nth-child(2)').addEventListener('click', function () {
    location.href = '/admin/user_info';
});

document.querySelector('#sub-chapters3 li:nth-child(1)').addEventListener('click', function () {
    console.log("Clicked on clients link");
    location.href = '/admin/clients_info';
});