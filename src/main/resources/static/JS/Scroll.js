$(function () {
  $(window).scroll(function () {
    let sct = $(window).scrollTop();
    let nav = $(".header nav").eq(1);

    if (sct > 10) {
      nav.addClass("fixed");
    } else if (sct < 10) {
      nav.removeClass("fixed");
    }
  });
});
