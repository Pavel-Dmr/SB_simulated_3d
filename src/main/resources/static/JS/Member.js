// 상시

$(function () {

  let id = Get_Cookie("cookie_id");
  console.log(id);
  if (id) {
    $("#id").val(id);
    $("#save_id").attr("checked", true);
  }
});

// 메서드 라인
function Form_Check(type) {
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");

  const data = String($("#" + type).val());

  let data_json = {data: data};

  switch (type) {
    case "nickname": {
      if (data == "") {
        alert("닉네임은 반드시 입력해야합니다.");
        $("#nickname").focus();
        return false;
      } else if (data.length < 6 || data.length > 12) {
        alert("닉네임은 6자이상 12자이하로만 가능합니다.");
        $("#nickname").focus();
        return false;
      }
      break;
    }
    case "email": {
      if (!Email_Form_Check(data)) {
        alert("이메일 형식이 맞지 않습니다.");
        return false;
      }
      break;
    }
  }

  $.ajax({
    type: "POST",
    url: "/member/" + type + "_check",
    dataType: "json",
    contentType: "application/json",
    data: JSON.stringify(data_json),
    cache: false,
    beforeSend: function (request) {
      request.setRequestHeader(header, token);
    },
    success: function (data) {
      // alert(data);
      switch (type) {
        case "nickname": {
          if (confirm("해당 닉네임은 사용 가능합니다. 사용하시겠습니까?")) {
            $("#" + type).attr("readonly", "readonly");
            $("#" + type + "_check").attr("hidden", "hidden");
            $("#" + type + "_reset").removeAttr("hidden");
          } else {
            return false;
          }
          break;
        }
        case "email": {
          if (confirm("해당 이메일은 사용가능합니다. 사용하시겠습니까?")) {
            $("#" + type).attr("readonly", "readonly");
            $("#" + type + "_check").attr("hidden", "hidden");
            $("#" + type + "_reset").removeAttr("hidden");
          } else {
            return false;
          }
          break;
        }
      }
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
  $("#" + type).removeAttr("readonly");
  $("#" + type + "_check").removeAttr("hidden");
  $("#" + type + "_reset").attr("hidden", "hidden");
}

function Sign_Check() {
  if ($("#nickname_check").attr("hidden") == "hidden" && $("#email_check").attr("hidden") == "hidden") {
    alert("정상 가입되었습니다.");
    return true;
  } else {
    alert("닉네임 또는 이메일이 중복 확인이 되지 않았습니다.");
    return false;
  }
}

function Login_Check() {
  let id_check = $("#save_id").is(":checked");
  let id = $("#id").val();
  let password = $("#password").val();
  let period = 30;

  if (id == "") {
    alert("이메일을 입력 해주세요");
    return false;
  } else if (password == "") {
    alert("비밀번호을 입력 해주세요");
    return false;
  }

  if (id_check) {
    Set_Cookie("cookie_id", id, period);
  } else {
    Delete_Cookie("cookie_id");
  }

  return true;
}

function Set_Cookie(cookie_name, value, period) {
  let date = new Date();
  date.setDate(date.getDate() + period);
  let cookie_value = escape(value) + (period == null ? "" : "; expires=" + date.toGMTString()  );
  document.cookie = cookie_name + "=" + cookie_value;
}

function Get_Cookie(cookie_name) {
  cookie_name = cookie_name + "=";

  let cookie = document.cookie;
  let start = cookie.indexOf(cookie_name);
  let cookie_value = "";

  if (start != -1) {
    start += cookie_name.length;
    var end = cookie.indexOf(";", start);
  }
  if (end == -1) {
    end = cookie.length;
  }
  cookie_value = cookie.substring(start, end);

  return unescape(cookie_value);
}

// 현재 일보다 더 이전으로 설정하면 자동으로 만료됩니다.
function Delete_Cookie(cookie_name) {
  let date = new Date();
  date.setDate(date.getDate());
  document.cookie = cookie_name + "=" + "; expires=" + date.toGMTString();
}
