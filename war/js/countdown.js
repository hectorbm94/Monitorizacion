function getTimeRemaining(endtime) {

	var t = endtime - Date.parse(new Date());
	var seconds = 0;
	var minutes = 0;
	var hours = 0;
	var days = 0;
	if (t > 0) {
		seconds = Math.floor((t / 1000) % 60);
		minutes = Math.floor((t / 1000 / 60) % 60);
		hours = Math.floor((t / (1000 * 60 * 60)) % 24);
		days = Math.floor(t / (1000 * 60 * 60 * 24));
	}
	return {
		'total': t,
		'days': days,
		'hours': hours,
		'minutes': minutes,
		'seconds': seconds
	};
}

function initializeClock(id, endtime) {




	function recurrencia(){
		var j = 0;

		for (i=0; i<endtime.length; i++){
			if(endtime[i] != 0){


				j = i+1;

				var clock = document.getElementById(id + j);
				var daysSpan = clock.querySelector('.days' + j);
				var hoursSpan = clock.querySelector('.hours' + j);
				var minutesSpan = clock.querySelector('.minutes' + j);
				var secondsSpan = clock.querySelector('.seconds' + j);

				function updateClock() {
					var t = getTimeRemaining(endtime[i]);

					daysSpan.innerHTML = t.days + 'd '+ ':';
					hoursSpan.innerHTML = ('0' + t.hours).slice(-2) + 'h ' + ':';
					minutesSpan.innerHTML = ('0' + t.minutes).slice(-2) + 'm ' + ':';
					secondsSpan.innerHTML = ('0' + t.seconds).slice(-2) + 's ';
				}

				updateClock();
			}

		}

}

	var timeinterval = setInterval(recurrencia, 1000);



}


var i = 1;
var arraymili = [];

while (document.getElementById("fechaFin" + i) != null){
	arraymili.push(parseFloat(document.getElementById("fechaFin" + i).value));
	i++;
}

initializeClock('clock', arraymili);

