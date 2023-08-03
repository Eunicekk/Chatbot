export function sendMessage(content, callback){
    $.ajax({
        url: '/gpt/question',
        type: 'post',
        data: JSON.stringify({content: content}),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function(result){
            let message = result.choices[0].message.content;
            callback(message);
        },
        error: function(a, b, c){
            console.error(c);
        }
    });
}