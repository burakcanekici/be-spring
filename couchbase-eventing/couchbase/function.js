function OnUpdate(doc, meta) {
    var request = {
        path: '/notify',
        body: {
            id : meta.id,
            value: doc
        }
    };
    var response = curl("POST", notifyApi, request);
    if(response.status != 200){
        log('request failed', response);
    }
}
function OnDelete(meta, options) {
}