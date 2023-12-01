<?php
    $input = file_get_contents('php://input');
    $lines = file("rooms/$input");

    file_put_contents("rooms/$input", rtrim($lines[0], "\n"));
?>