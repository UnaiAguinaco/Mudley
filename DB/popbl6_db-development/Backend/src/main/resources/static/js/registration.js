$("input:checkbox").on('click', function () {
    var $box = $(this);
    if ($box.is(":checked")) {
      var group = "input:checkbox[class='" + $box.attr("class") + "']";
      $(group).prop("checked", false);
      $box.prop("checked", true);
    } else {
      $box.prop("checked", false);
    }
    
    if ($box.attr('id')=="grupoCheck") {
      document.querySelector(".groupAddData").classList.remove("hide");
    }else if ($box.attr('id')=="asociacionCheck") {
      document.querySelector(".groupAddData").classList.add("hide");
    }
  });