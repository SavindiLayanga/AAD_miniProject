$(document).ready(function() {
    $("#btn-submit").click(function() {
        let desc = $("#form-description").val();
        let qty = $("#form-qty").val();
        let unitPrice = $("#form-unitPrice").val();

        console.log(desc);
        console.log(qty);
        console.log(unitPrice);

        const itemData = {
            desc: desc,
            qty: qty,
            unitPrice: unitPrice
        };

        console.log(itemData);

        // create JSON
        const itemJson = JSON.stringify(itemData);
        console.log(itemJson)
        sendAjax(itemJson);

        // send the data to the backend via Ajax
        // const sendAjax = (itemJson)=>{
        //     const http = new XMLHttpRequest();
        //
        //     ///////////////////////////////////////////////////////////////////
        //
        //     http.onreadystatechange = () =>{
        //         if (http.readyState == 4 && http.status == 200) {
        //             console.log("Success")
        //             console.log(http.readyState)
        //         }else {
        //             console.log("Failed")
        //             console.log(http.readyState)
        //         }
        //     }
        //
        //     //////////////////////////////////////////////////////////////////
        //     http.open("POST", "http://localhost:8080/pos/item", true);
        //     http.setRequestHeader("Content-Type","application/json");
        //     http.send(itemJson)
        // }

        $.ajax({
            url:"http://localhost:8080/pos/item",
            type:"POST",
            data:itemData,
            headers:{"Content-Type":"application/json"},
            success: (res) => {
                console.log(JSON.stringify(res))
            },
            error
        })
    });
});
