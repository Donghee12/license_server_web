
const emailCheck = () => {
    const email = document.getElementById("memberEmail").value;
    const checkResult = document.getElementById("check-result");
    console.log("입력값: ", email);

    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

    if (!emailPattern.test(email)) {
        checkResult.style.color = "red";
        checkResult.innerHTML = "올바른 이메일 형식이 아닙니다.";
        return;
    }

    $.ajax({
        // 요청방식: post, url: "email-check",데이터: 이메일
        type: "post",
        url: "/member/email-check",
        data: {
            "memberEmail" : email
        },
        success:function(res){
            console.log("요청성공",res);
            if (res == "ok"){
                console.log("사용가능한 이메일");
                checkResult.style.color = "green";
                checkResult.innerHTML = "사용가능한 이메일";
            }else{
                console.log("이미 사용중인 이메일")
                checkResult.style.color = "red";
                checkResult.innerHTML = "이미 사용중인 이메일";
            }
        },
        error:function(err){
            console.log("에러발생",err);
        }
    });
}
function validatePassword() {
    // 비밀번호 입력 필드의 값을 가져오기
    var password = document.getElementById('password').value;
    var password1 = document.getElementById('password1').value;

    // 간단한 유효성 검사 로직 예시
    if (password.length < 8) {
        alert('비밀번호는 8자 이상이어야 합니다.');
        return false; // 폼 전송을 막음
    }

    // 특수문자 포함 여부 확인을 위한 정규표현식
    var specialCharRegex = /[!@#$%^&*(),.?":{}|<>]/;

    if (!specialCharRegex.test(password)) {
        alert('비밀번호에 특수문자를 최소한 하나 이상 포함해주세요.');
        return false;
    }

    if (password !== password1) {
        alert('비밀번호와 비밀번호 확인 값이 일치하지 않습니다.');
        return false;
    }

    var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    var email = document.getElementById('email').value;
    if (!emailRegex.test(email)) {
        alert('올바른 이메일 형식이 아닙니다.');
        return false;
    }



    return true; // 폼 전송을 진행
}
