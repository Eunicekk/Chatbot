let $input = $('#post-image');
let $imgList = $('.img-list');
// console.log($input);

// file change이벤트로 미리보기 갱신하기
$input.on('change', function () {
    let files = this.files;
    //   console.log(files);

    // 길이 체크함수 (files, 원하는 길이)
    let newFiles = checkLength(files, 4);

    appendImg(newFiles);
});

// 클릭 이벤트로 이미지 지우고 미리보기 갱신하기
$imgList.on('click', function (e) {
    let name = $(e.target).data('name');
    removeImg(name);
    appendImg($input[0].files);
});

//미리보기 삭제 함수
function removeImg(name) {
    let dt = new DataTransfer();

    let files = $input[0].files;

    for (let i = 0; i < files.length; i++) {
        if (files[i].name !== name) {
            dt.items.add(files[i]);
        }
    }
    $input[0].files = dt.files;
}

//파일 길이 체크 함수(체크할 files객체, 제한할 길이)
function checkLength(files, num) {
    if (files.length > num) {
        alert(`파일은 최대 ${num}개까지만 첨부 가능합니다.`);
        // 최대 수를 넘으면 비어있는 files객체 반환
        return new DataTransfer().files;
    }
    return files;
}

// 이미지 미리보기 처리 함수
// 이미지 수가 4개보다 적으면 기본이미지로 대체함
function appendImg(files) {
    for (let i = 0; i < 4; i++) {
        if (i < files.length) {
            let src = URL.createObjectURL(files[i]);

            $imgList.eq(i).css('background-image', `url(${src})`).css('background-size', 'cover').data('name', `${files[i].name}`);

            $imgList.eq(i).addClass('x-box');
        } else {
            $imgList
                .eq(i)
                .css(
                    'background',
                    'url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIzNiIgaGVpZ2h0PSIzNiI+PGcgZmlsbD0ibm9uZSIgZmlsbC1ydWxlPSJldmVub2RkIj48ZyBzdHJva2U9IiNCNUI1QjUiIHN0cm9rZS13aWR0aD0iMS41IiB0cmFuc2Zvcm09InRyYW5zbGF0ZSg0IDQpIj48cmVjdCB3aWR0aD0iMjgiIGhlaWdodD0iMjgiIHJ4PSIzLjUiLz48Y2lyY2xlIGN4PSI4LjU1NiIgY3k9IjguNTU2IiByPSIyLjMzMyIvPjxwYXRoIGQ9Ik0yOCAxOC42NjdsLTcuNzc3LTcuNzc4TDMuMTExIDI4Ii8+PC9nPjxwYXRoIGQ9Ik0wIDBoMzZ2MzZIMHoiLz48L2c+PC9zdmc+) no-repeat 50% #f2f2f2'
                )
                .data('name', null);
            $imgList.eq(i).removeClass('x-box');
        }
    }
}