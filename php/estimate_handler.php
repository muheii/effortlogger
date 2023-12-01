<?php
    $input = file_get_contents('php://input');
    $input_array = explode(",", $input);

    $user_exists = False;

    if(file_exists("rooms/$input_array[0]")) {
        $lines = file("rooms/$input_array[0]", FILE_IGNORE_NEW_LINES);

        if (sizeof($lines) == 1) {
            file_put_contents("rooms/$input_array[0]", "\n$input_array[1],\n$input_array[2],", FILE_APPEND);
        }

        if (sizeof($lines) == 3) {
            $uuid_array = explode(",", $lines[1]);

            foreach ($uuid_array as $uuid) {
                if ($uuid == $input_array[1]) {
                    $user_exists = True;
                    echo "USER_EXISTS";
                }
            }
            
            if (!$user_exists) {
                $lines[1] = $lines[1] . $input_array[1] . ",";
                $lines[2] = $lines[2] . $input_array[2] . ",";

                file_put_contents("rooms/$input_array[0]", implode("\n", $lines));
            }
        }
    }
?>