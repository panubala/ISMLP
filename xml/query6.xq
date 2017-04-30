let $i := doc('inproceedings.xml')//inproceedings
return
<root>{
  <item>{
    <avg>{
      count($i//author) div count($i)
    }</avg>
  }</item>
}</root>