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
            plugins : ['dnd','changed']
        })
    }

    $('#js_tree').bind('move_node.jstree', function (e, data) {
        var params = {
            id : data.node.id,
            parent : data.node.parent,
            text : data.node.text
        };
        moveCategory(params);
        console.log("move_node", params);
    });
    function moveCategory(treeObject) {
        $.ajax({
            url : '/moveCategory',
            data : JSON.stringify(treeObject),
            dataType : 'json',
            type : 'POST',
            contentType: 'application/json',
            success: function (resp) {
                console.log('Category moved: ' + resp.code)
            },
            error : function (e) {
                console.log('Error ' + e)
                
            }
        })
    }


</script>
</html>