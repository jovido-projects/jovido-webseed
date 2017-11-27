jQuery(document).ready(function($) {
    $('.btn-file').change(function() {
        var form = $(this).closest('form');
        var input = $(this).children('input[type="file"]');
        var formaction = input.attr('formaction');
        form.attr('action', formaction);
        form.attr('enctype', 'multipart/form-data');
        form.submit();
    });
});