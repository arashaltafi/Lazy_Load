<?php

header("Access_Control_Allow_Orogon:*");
header("Content_Type:application/json;charset=UTF-8");
header("Access_Control_Allow_Methods:POST");
header("Access_Control_Max_Age:3600");
header("Access_Control_Allow_Headers:Content-Type,Access_Control_Allow_Headers,Authorozation,X-Requested-with");

// Define database credentials
$host="localhost";
$username = "******";
$password="******";
$dbname="******";

// Connect to the database
$conn = mysqli_connect($host, $username, $password, $dbname);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Define page number and page size
$page_number = isset($_GET['page_number']) ? $_GET['page_number'] : 1;
$page_size = isset($_GET['page_size']) ? $_GET['page_size'] : 10;

// Calculate offset and limit
$offset = ($page_number - 1) * $page_size;
$limit = $page_size;

// Define query to fetch items
$sql = "SELECT * FROM test_paging LIMIT $offset, $limit";

// Execute query
$result = mysqli_query($conn, $sql);


// Define response object
$response = array();

// Check if any items are found
if (mysqli_num_rows($result) > 0) {
    // Loop through each item and add it to the response
    while ($row = mysqli_fetch_assoc($result)) {
        array_push($response, $row);
    }
}

// Return response as JSON
header('Content-Type: application/json');
echo json_encode($response);

// Close database connection
mysqli_close($conn);

?>