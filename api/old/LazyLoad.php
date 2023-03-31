<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

error_reporting(E_ALL ^ E_NOTICE);  

$page = isset($_GET['page']) ? $_GET['page'] : 1;

$records_per_page=isset($_GET['per_page']) ? $_GET['per_page'] : 5;

$from_record_num = ($records_per_page * $page) - $records_per_page;

include_once 'database.php';


        
class Product
{
    private $conn;
    private $table_name = "bazigars";
    public function __construct($db)
    {
        $this->conn = $db;
    }
    
    public function readPaging($from_record_num,$records_per_page)
    {
        $json=file_get_contents('php://input');
        $comments=json_decode($json);
        // $name=$comments->{'name'};
        
        //Method Get Data
        $name=$_GET['name'];
        
        //Method Post Data
        // $name=$_POST['name'];

        //Manually
        // $name = "آزاده صمدی";
        
        $query = "SELECT * FROM bazigars WHERE name = '$name' AND status='1' ORDER BY id DESC LIMIT ?, ?";
        $stmt = $this->conn->prepare( $query );
        $stmt->bindParam(1, $from_record_num, PDO::PARAM_INT);
        $stmt->bindParam(2, $records_per_page, PDO::PARAM_INT);
        $stmt->execute();
        return $stmt;
    }
    
     public function count()
     {
        $query = "SELECT * FROM bazigars WHERE name = '$name' AND status='1' ORDER BY id DESC LIMIT ?, ?";
        $stmt = $this->conn->prepare( $query );
        $stmt->execute();
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        return $row['total_rows'];
     }
     
}

$database = new Database();
$db = $database->getConnection();
$product = new Product($db);
$stmt = $product->readPaging($from_record_num, $records_per_page);
$num = $stmt->rowCount();


if($num>0)
{
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC))
    {
	    $record["name"]=$row["name"];
	    $record["image"]=$row["image"];
	    $statictic[]=$record;
    }
    echo json_encode($statictic);
}
else
{
    echo '[]';
}


?>