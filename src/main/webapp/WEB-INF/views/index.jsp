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
       populateJsTree();
    });
    $('#js_tree').bind('move_node.jstree', function (e, data) {
        var params = {
            id : data.node.id,
            parent : data.node.parent,
            text : data.node.text
        };
        _moveCategory(params);
        console.log("move_node", params);
    }).on('select_node.jstree', function (e, data) {
        console.log("Selected " + data.node.text);
        $('#update_area').val(data.node.text);
        $('#selected_id').val(data.node.id);
    }).on('deselect_node.jstree', function (e, data) {
        $('#selected_id').val('#');
    });

    
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
        _addElement(newNode);
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
                text: text
            };
            _moveCategory(params);
            $('#js_tree').jstree('rename_node', node, text);
        }
    }

    function deleteButton() {
        var tree = $('#js_tree');
        console.log(tree.size());
        var node_id = tree.jstree('get_selected');
        $('#selected_id').val('#');
        _deleteElement(node_id);
        tree.jstree(true).delete_node(node_id);
        console.log(node_id);
    }

    function populateJsTree(jsonData) {
        $('#js_tree').jstree({
            core : {
                data: {
                    type: 'post',
                    url: '/getJSONTree',
                    dataType: 'json',
                    contentType: 'application/json',
                    data : function (node) {
                        return JSON.stringify(node.id);
                    }
                },
                check_callback : true
            },
            plugins : ['dnd','changed']
        })
    }

    function _getJsTree(parent) {
        $.ajax({
            async: false,
            type: 'post',
            url: '/getJSONTree',
            dataType: 'json',
            contentType: 'application/json',
            data : JSON.stringify(parent),
            success: function (json) {
                $.each(json, function (index, value) {
                    var tree = $('#js_tree');
                    var parent = tree.jstree(true).get_node(value.parent);
                    tree.jstree('create_node', parent, value, 'last');
                });
                $('#js_tree').jstree(true).redraw();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status);
                alert(thrownError);
            }
        });
    }



    function _deleteElement(treeObject) {
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

    function _moveCategory(treeObject) {
        $.ajax({
            url : '/updateElement',
            data : JSON.stringify(treeObject),
            dataType : 'json',
            type : 'POST',
            contentType: 'application/json',
            success: function (resp) {
                console.log('Category moved: ' + resp.id)
            },
            error : function (e) {
                console.log('Error ' + e)
                
            }
        })
    }

    function _addElement(newElement) {
        $.ajax({
            url : '/updateElement',
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