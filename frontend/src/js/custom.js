// to get current year
function getYear () {
    const currentDate = new Date()
    const currentYear = currentDate.getFullYear()
    document.querySelector('#displayYear').innerHTML = currentYear
}

getYear()
