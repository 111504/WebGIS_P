
function getKMLData(apiUrl) {
    return axios.get(apiUrl)
      .then(response => {
       
        if(response.data.status == '200' ){
           // 返回處理好的 KML 數據
           return response.data.data;  // 假設返回的數據結構是 { data: ... }
        }
        if(response.data.status == '404' ){
          console.warn("Error"+response.data.message)
       }
      })
      .catch(error => {
        console.error("Error fetching KML data:", error);
        throw error;  // 向上拋出錯誤以便在其他地方處理
      });
  }


  function postKMLData(apiUrl, inputForm) {
    return axios.post(apiUrl, inputForm)  // 使用 POST 方法，傳遞 inputForm 內容
      .then(response => {
        if (response.data.status == '200') {
          // 返回處理好的 KML 數據
          return response.data.data;  // 假設返回的數據結構是 { data: ... }
        }
        else {
          console.warn("Error: " + response.data.message);
        }
      })
      .catch(error => {
        console.error("Error posting KML data:", error);
        throw error;  // 向上拋出錯誤以便在其他地方處理
      });
  }