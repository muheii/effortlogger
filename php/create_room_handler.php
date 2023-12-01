<?php
    $roomName = file_get_contents('php://input');
    $roomExists = False;

    foreach (file("activeRooms") as $line) {
        if ($line == $roomName.PHP_EOL) {
            $roomExists = True;
            echo "ROOM_EXISTS";
        }
    }

    if(!$roomExists) {
        file_put_contents("activeRooms", $roomName.PHP_EOL, FILE_APPEND);
        echo "SUCCESS";
    }
?>