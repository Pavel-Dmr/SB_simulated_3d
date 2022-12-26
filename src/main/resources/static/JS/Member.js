// 메서드 라인
function Nickname_Check() {
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");

  const nickname = String($("#nickname").val());
  let data = {nickname: nickname};

  if (nickname == "") {
    alert("닉네임은 반드시 입력해야합니다.");
    $("#nickname").focus();
    return false;
  } else if (nickname.length < 6 || nickname.length > 12) {
    alert("닉네임은 6자이상 12자이하로만 가능합니다.");
    $("#nickname").focus();
    return false;
  }

  $.ajax({
    type: "POST",
    url: "/member/nickname_check",
    dataType: "json",
    contentType: "application/json",
    data: JSON.stringify(data),
    cache: false,
    beforeSend: function (request) {
      request.setRequestHeader(header, token);
    },
    success: function (data) {
      alert(data);
    },
    error: function (request, error, data) {
      if ((request.status = "401")) {
        alert(request.responseText);
      } else {
        alert(request + " = " + error + " = " + data);
      }
    },
  });
}

/* 입력 이메일 형식 유효성 검사 */
function Email_Form_Check(email) {
  var form = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
  return form.test(email);
}

function Reset(type) {
  $("#" + type).attr("readonly", false);
  $("#" + type + "_check").attr("disabled", false);
  $("#" + type + "_check").attr("display", "inline-block");
  $("#" + type + "_reset").attr("disabled", true);
  $("#" + type + "_reset").attr("display", "none");
}
