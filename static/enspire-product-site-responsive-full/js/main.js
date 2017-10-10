$(document).ready(function() {

  $('.navbar-toggler').on('click', function() {
    var collapseTargetID = $(this).data('target');
    var collapseTarget = $(collapseTargetID);
    var hideOnCollapse = $('.featured .text-block');
    if (!collapseTarget.hasClass('collapsing')) {
      if (!collapseTarget.hasClass('show')) {
        hideOnCollapse.css('opacity', 0);
      } else {
        hideOnCollapse.css('opacity', 1);
      }
    }
  });

});
