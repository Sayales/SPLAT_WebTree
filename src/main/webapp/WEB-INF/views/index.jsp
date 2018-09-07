<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<html>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>
    <head>
        <title>WebTree struct</title>
    </head>
    <body>
    <div id="js_tree"></div>
    <br>
    <p id="load_text"></p>
    </body>
<script>
    function loading(miliseconds) {
            var currentTime = new Date().getTime();
            while (currentTime + miliseconds >= new Date().getTime()) {
            }
    }
    $(function () {
        $.ajax({
         async: false,
         type: 'get',
         url: '/getJSONTree',
         dataType: 'json',

         success: function (json) {
             populateJsTree(json)
         },
         error: function (xhr, ajaxOptions, thrownError) {
         alert(xhr.status);
         alert(thrownError);
         }
         });

        }
    );
    function populateJsTree(jsonData) {
        $('#js_tree').jstree({
            core : {
                data: jsonData,
                check_callback : true
            },
            plugins : ['dnd']
        })
    }



</script>
</html>