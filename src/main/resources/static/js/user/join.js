let $genderBox = $('.gender-box');

$genderBox.on('click', function (e) {
    let idx = $genderBox.index(this);
    console.log(idx);
    for (let i = 0; i < $genderBox.length; i++) {
        if (i == idx) {
            $genderBox.eq(i).addClass('checked');
        } else {
            $genderBox.eq(i).removeClass('checked');
        }
    }
});