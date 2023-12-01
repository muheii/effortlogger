<?php
    $roomName = file_get_contents('php://input');
    $activeRooms = file_get_contents('activeRooms');

    foreach (file("activeRooms") as $line) {
        if ($line == $roomName.PHP_EOL) {
            $line_array = explode(",", $line);

            if(sizeof($line_array) == 2) {
                $mem_count = int($line_array[1]);

                $mem_count = $mem_count + 1;
            }

            $activeRooms = str_replace($line, '', $activeRooms);
            file_put_contents('activeRooms', $activeRooms . "\n" . $line_array[0] . "," . $mem_count);
        }
    }
?>