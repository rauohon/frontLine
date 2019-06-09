function fn_attckPtCalc (str, dex, con) {
	var result = Math.round((str + dex/2 + con) * 2);
	return result;
}

function fn_defencePtCalc (str, con) {
	var result = (str + con) * 2;
	return result;
}

function fn_actionPtCalc (str, con) {
	var result = (Math.round(str/2) + con) * 3;
	return result;
}

function fn_hpCalc (str, con) {
	var result = (str * 4 + con * 6) * 2;
	return result;
}