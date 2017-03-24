
String.prototype.startsWith = function(prefix){
try {
if (this.indexOf(prefix, 0) === 0) {
return true;}
else {
return false;}}
catch (e) {
return false;}};
String.prototype.endsWith = function(suffix){
try {
var length = this.length;
var pe = this.lastIndexOf(suffix, length);
if (pe == -1) {
return false;}
else {
if (pe + suffix.length == length) {
return true;}
else {
return false;}}}
catch (e) {
return false;}};
String.prototype.endsWithArray = function(array){
try {
for (var i = 0; i < array.length; i++) {
if (this.endsWith(array[i])) {
return true;}}}
catch (e) {
return false;}
return false;};
String.prototype.replaceAll = function(regex, replacement){
try {
var str = this, ps = 0;
ps = str.indexOf(regex, ps);
if (ps == -1) {
return this;}
while (ps != -1) {
str = str.substring(0, ps) + str.substring(ps, str.length).replaceFirst(regex, replacement);
ps = ps + replacement.length;
ps = str.indexOf(regex, ps);}}
catch (e) {
return this;}
return str;};
String.prototype.replaceFirst = function(regex, replacement){
return this.replace(regex, replacement);};
String.prototype.trim = function(){
try {
return this.replace(/(^\s*)|(\s*$)/g, "");}
catch (e) {
return this;}};
String.prototype.toCharArray = function(){
try {
var arr = new Array(this.length);
for (var i = 0; i < this.length; i++) {
arr[i] = this.charAt(i);}
return arr;}
catch (e) {
throw new Error("Array String.toCharArray() method exception, " + e.message);}};
String.prototype.equals = function(aString){
try {
if (this === aString) {
return true;}
else {
return false;}}
catch (e) {
return false;}};
String.prototype.compareToIgnoreCase = function(aString){
try {
if (this.toLowerCase() === aString.toLowerCase()) {
return 0;}
else {
return this.length;}}
catch (e) {
throw new Error("int String.compareToIgnoreCase(String aString) method exception, " + e.message);}};
String.prototype.isChineseIdentifyNo15 = function(){
var _id=this;
for(var i=0;i<_id.length;i++){
if(_id.charAt(i)<'0'||_id.charAt(i)>'9'){
return false;
break;}}
var year=_id.substr(6,2);
var month=_id.substr(8,2);
var day=_id.substr(10,2);
if(year<'01'||year >'90')return false;
if(month<'01'||month >'12')return false;
if(day<'01'||day >'31')return false;
return true;};
String.prototype.isChineseIdentifyNo18 = function(){
var powers=new Array("7","9","10","5","8","4","2","1","6","3","7","9","10","5","8","4","2");
var parityBit=new Array("1","0","X","9","8","7","6","5","4","3","2");
var _id=this;
var _num=_id.substr(0,17);
var _parityBit=_id.substr(17);
var _power=0;
for(var i=0;i< 17;i++){
if(_num.charAt(i)<'0'||_num.charAt(i)>'9'){
return false;
break;}
else{
_power+=parseInt(_num.charAt(i))*parseInt(powers[i]);}}
var mod=parseInt(_power)%11;
if(parityBit[mod]==_parityBit){
return true;}
return false;};
String.prototype.isChineseIdentifyNo = function(){
if (this.length == 15) {
return this.isChineseIdentifyNo15();}
else if (this.length == 18) {
return this.isChineseIdentifyNo18();}
else{
return false;}};
Number.prototype.formatMoney = function(radixNum){
var str = this.toFixed(radixNum);
var arr = str.toCharArray();
var start = false, cnt = 0;
str = ""; //00.333,333
for(var i=arr.length-1; i>=0; i--){
str = arr[i] + str;
if(start) cnt++;
if(cnt==3 && i!=0){
str = "," + str;
cnt = 0;}
if(arr[i]==".") start = true;}
return str;};
Number.prototype.getInteger = function(){
var str = this.toString();
var p = str.indexOf(".");
if(p==-1){
return str;}
else{
return str.substr(0,p);}};
Date.prototype.YEAR = 1;
Date.prototype.MONTH = 2;
Date.prototype.WEEK_OF_YEAR = 3;
Date.prototype.WEEK_OF_MONTH = 4;
Date.prototype.DATE = 5;
Date.prototype.DAY_OF_MONTH = 5;
Date.prototype.DAY_OF_YEAR = 6;
Date.prototype.DAY_OF_WEEK = 7;
Date.prototype.HOUR = 10;
Date.prototype.MINUTE = 12;
Date.prototype.SECOND = 13;
Date.prototype.MILLISECOND = 14;
Date.prototype.add = function(field, amount){
switch(field){
case 1: //YEAR
this.setYear(this.getYear() + amount);
break;
case 2: //MONTH
this.setMonth(this.getMonth() + amount);
break;
case 5: //DATE
this.setDate(this.getDate() + amount);
break;
case 10: //HOUR
this.setHours(this.getHours() + amount);
break;
case 12: //MINUTE
this.setMinutes(this.getMinutes() + amount);
break;
case 13: //SECOND
this.setSeconds(this.getSeconds() + amount);
break;
case 14: //MILLISECOND
this.setMilliseconds(this.getMilliseconds() + amount);
break;}};
Date.prototype.after = function(when){
try{
if(this.valueOf() > when.valueOf()){
return true;}else{
return false;}}catch(e){
return false;}};
Date.prototype.before = function(when){
try{
if(this.valueOf() < when.valueOf()){
return true;}else{
return false;}}catch(e){
return false;}};
Date.prototype.equals = function(obj){
try{
if(this.toString() == obj.toString()){
return true;}else{
return false;}}catch(e){
return false;}};
Date.prototype.isValid = function(year, month, day){
var d = new Date();
d.setFullYear(year);
d.setMonth(month-1);
d.setDate(day);
if(d.getFullYear()==year && d.getMonth()==month-1 && d.getDate()==day)
return true;
else
return false;};
Date.prototype.getLastDayOfMonth = function(year, month){
var d = new Date();
d.setFullYear(year);
d.setMonth(month);
d.setDate(1);
return new Date(d.getTime()-(24*60*60*1000)).getDate();};
Date.prototype.format = function(pattern){
pattern = pattern.replaceFirst("yyyy", this.getFullYear());
var m = this.getMonth()+1;
var MM = m<10 ? "0"+m : m;
pattern = pattern.replaceFirst("MM", MM);
return pattern;};
Array.prototype.equals = function(arr){
try{
if(this.toString() == arr.toString()){
return true;}else{
return false;}}catch(e){
return false;}};
Array.prototype.clone = function(){
try{
var newArr = new Array(this.length);
for(var i=0; i<this.length; i++){
newArr[i] = this[i];}
return newArr;}catch(e){
throw new Error("Array Array.clone() method exception, " + e.message);}};
Array.prototype.wrap = function(prefix, suffix){
try{
if(prefix.length != 1 || suffix.length != 1){
throw new Error("require two char datatype parameters.");}
for(var i=0; i<this.length; i++){
this[i] = prefix + this[i] + suffix;}}catch(e){
throw new Error("void Array.wrap(char prefix, char suffix) method exception, " + e.message);}};
var ajax = new function(){
this.send = function(url, msg, callback){
var al = document.createElement("a");
al.href = "";
al = al.href;
var rn = new Date().getTime() + "0" + Math.random();
url += (url.indexOf("?") == -1 ? "?" : "&");
url += "A" + rn + "=" + rn;
var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
xmlhttp.open("POST", al + url, true);
xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
xmlhttp.setRequestHeader("If-Modified-Since", "0");
xmlhttp.onreadystatechange = function(){
if (xmlhttp.readyState == 4) {
if (xmlhttp.status == 200) {
var str = new String(xmlhttp.responseText);
callback(str.trim());}
else {
callback('{"error":"can not connect to server."}');}
xmlhttp = null;}};
xmlhttp.send("content=" + encodeURI(msg));};
return this;};