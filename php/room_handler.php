<?php
    $input = file_get_contents('php://input');

    $input_array = explode(",", $input);
    file_put_contents("rooms/$input_array[0]", $input_array[1]);
?>