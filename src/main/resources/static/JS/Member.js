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
