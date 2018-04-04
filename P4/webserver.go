package main
//curl -H "Content-Type: application/json" -d '{"Maker":2, "Model":3,"Nofdays":4,"Nofunits":6}' http://localhost:8080/carrentalnew
import (
    "fmt"
    "log"
    "net/http"
    "github.com/gorilla/mux"
    "encoding/json"
    "io"
    "io/ioutil"
    "encoding/csv"
    "os"
    "strconv"
    "bufio"

)

type ResponseMessage struct {
    Field1 string
    Field2 string
}
type RequestMessage struct {
    Maker int
    Model int
    Nofdays int
    Nofunits int
}



func main() {

router := mux.NewRouter().StrictSlash(true)
router.HandleFunc("/", Index)
//router.HandleFunc("/endpoint/{param}", endpointFunc)
//router.HandleFunc("/endpoint2/{param}", endpointFunc2JSONInput)
router.HandleFunc("/carrentalnew",carrentalFuncnew)
router.HandleFunc("/carrentalread",carrentalFuncread)

log.Fatal(http.ListenAndServe(":8080", router))
}

func Index(w http.ResponseWriter, r *http.Request) {
    fmt.Fprintln(w, "Service OK")
}
func carrentalFuncnew(w http.ResponseWriter, r *http.Request) {
	var requestMessage RequestMessage
    body, err := ioutil.ReadAll(io.LimitReader(r.Body, 1048576))
    if err != nil {
        panic(err)
    }
    if err := r.Body.Close(); err != nil {
        panic(err)
    }
    if err := json.Unmarshal(body, &requestMessage); err != nil {
        w.Header().Set("Content-Type", "application/json; charset=UTF-8")
        w.WriteHeader(422) // unprocessable entity
        if err := json.NewEncoder(w).Encode(err); err != nil {
            panic(err)
        }
    } else {
		maker := requestMessage.Maker
		model := requestMessage.Model
		nofdays := requestMessage.Nofdays
		nofunits := requestMessage.Nofunits
		
		total := maker * model * nofdays * nofunits
		fmt.Println("Resumen de compra\n")
		fmt.Print("Marca: ")
		fmt.Println(maker)
		fmt.Print("Model: ")
		fmt.Println(model)
		fmt.Print("Numero de dias: ")
		fmt.Println(nofdays)
		fmt.Print("Numero de unidades: ")
		fmt.Println(nofunits)
		fmt.Println("--------------------------------------------------")
		fmt.Print("Total: ")
		fmt.Println(total)
		writeToFile(w,maker,model,nofdays,nofunits)
		
	}
}

func carrentalFuncread(w http.ResponseWriter, r *http.Request){
	var rental RequestMessage
	var rentals []RequestMessage
	file, err := os.Open("rentals.csv")
    if err!=nil {
		json.NewEncoder(w).Encode(err)
		return
    }
    reader := csv.NewReader(bufio.NewReader(file))
    for {
        record, err := reader.Read()
        if err == io.EOF {
           break
        }
        rental.Maker , _ = strconv.Atoi(record[0])
        rental.Model, _ = strconv.Atoi(record[1])
        rental.Nofdays, _ = strconv.Atoi(record[2])
        rental.Nofunits, _ = strconv.Atoi(record[3])
        rentals = append(rentals, rental)
    }
		jsonData, err := json.Marshal(rentals)
		if err != nil {
			fmt.Println(err)
			os.Exit(1)
		}
		fmt.Fprintf(w, "%q", string(jsonData))
		
	
}

func writeToFile(w http.ResponseWriter, make int, model int, nofdays int, nofunits int) {
    file, err := os.OpenFile("rentals.csv", os.O_APPEND|os.O_WRONLY|os.O_CREATE, 0600)
    if err!=nil {
        json.NewEncoder(w).Encode(err)
        return
    }
    writer := csv.NewWriter(file)
    var data1 = []string{strconv.Itoa(make),strconv.Itoa(model),strconv.Itoa(nofdays),strconv.Itoa(nofunits)}
    writer.Write(data1)
    writer.Flush()
    file.Close()
}


/*func endpointFunc(w http.ResponseWriter, r *http.Request) {
    vars := mux.Vars(r)
    param := vars["param"]
    res := ResponseMessage{Field1: "Text1", Field2: param}
    json.NewEncoder(w).Encode(res)
    
}

func endpointFunc2JSONInput(w http.ResponseWriter, r *http.Request) {
    var requestMessage RequestMessage
    body, err := ioutil.ReadAll(io.LimitReader(r.Body, 1048576))
    if err != nil {
        panic(err)
    }
    if err := r.Body.Close(); err != nil {
        panic(err)
    }
    if err := json.Unmarshal(body, &requestMessage); err != nil {
        w.Header().Set("Content-Type", "application/json; charset=UTF-8")
        w.WriteHeader(422) // unprocessable entity
        if err := json.NewEncoder(w).Encode(err); err != nil {
            panic(err)
        }
    } else {
        fmt.Fprintln(w, "Successfully received request with Field1 =", requestMessage.Field1)
        fmt.Println(r.FormValue("queryparam1"))
    }
}*/

