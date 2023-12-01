<?php
    $input = file_get_contents('php://input');

    if(file_exists("rooms/$input")) {
        $lines = file("rooms/$input", FILE_IGNORE_NEW_LINES);

        if(sizeof($lines) >= 1) {
            echo $lines[0];
            if(sizeof($lines) == 3) {
                echo "~";
                echo $lines[2];
            }
        }
    }
?>