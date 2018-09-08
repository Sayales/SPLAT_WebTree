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
    <button onclick="deleteButton()">Delete</button>
    <button onclick="updateButton()">Update</button>
    <button onclick="addButton()">Add</button>
    <br>
    <label for="update_area">Update or Add: </label><input id="update_area" type="text"/>
    <input id="selected_id" type="text" hidden/>


    </body>
<script>
    $(function () {
       getJsTree()
    });
    $('#js_tree').bind('move_node.jstree', function (e, data) {
        var params = {
            id : data.node.id,
            parent : data.node.parent,
            text : data.node.text
        };
        moveCategory(params);
        console.log("move_node", params);
    }).on('select_node.jstree', function (e, data) {
        console.log("Selected " + data.node.text);
        $('#update_area').val(data.node.text);
        $('#selected_id').val(data.node.id);
    });

    function loading(miliseconds) {
            var currentTime = new Date().getTime();
            while (currentTime + miliseconds >= new Date().getTime()) {
            }
    }
    
    function addButton() {
        var parentId = $('#selected_id').val();
        if (!parentId) {
            parentId = "#";
        }
        var newData = $('#update_area').val();
        var newNode = {
            text : newData,
            parent : parentId
        };
        addElement(newNode);
        $('#js_tree').jstree(true).redraw();
    }

    function updateButton() {
        var node_id = $('#selected_id').val();
        if (node_id) {
            console.log("Node_id: " + node_id);
            var node = $('#js_tree').jstree(true).get_node(node_id);
            var text = $('#update_area').val();
            console.log("Node: " + JSON.stringify(node));
            var params = {
                id: node.id,
                parent: node.parent,
                text: node.text
            };
            moveCategory(params);
            $('#js_tree').jstree('rename_node', node, text);
        }
    }

    function deleteButton() {
        var node_id = $('#js_tree').jstree('get_selected');
        deleteElement(node_id);
        $('#js_tree').jstree(true).delete_node(node_id);
        console.log(node_id);
    }
    function getJsTree() {
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

    $('#js_tree').bind('move_node.jstree', function (e, data) {
        var params = {
            id : data.node.id,
            parent : data.node.parent,
            text : data.node.text
        };
        moveCategory(params);
        console.log("move_node", params);
    });

    function populateJsTree(jsonData) {
        $('#js_tree').jstree({
            core : {
                data: jsonData,
                check_callback : true
            },
            plugins : ['dnd','changed']
        })
    }

    function deleteElement(treeObject) {
        $.ajax({
            url : '/deleteElement',
            data : JSON.stringify(treeObject),
            dataType : 'json',
            type : 'POST',
            contentType: 'application/json',
            success: function (resp) {
                console.log('Element deleted: ' + resp.code)
            },
            error : function (e) {
                console.log('Error ' + e)

            }
        })
    }

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

    function addElement(newElement) {
        $.ajax({
            url : '/addElement',
            data : JSON.stringify(newElement),
            dataType : 'json',
            type : 'POST',
            contentType: 'application/json',
            success: function (resp) {
                console.log("New node: " + JSON.stringify(resp));
                var tree = $('#js_tree');
                var parent = tree.jstree(true).get_node(resp.parent);
                tree.jstree('create_node', parent, resp, 'last');
            },
            error : function (e) {
                console.log('Error ' + e)
            }
        })
    }


</script>
</html>