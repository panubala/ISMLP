let $i := doc('inproceedings.xml')/root/*
return
<root>{
  <item>{
    <avg>{
      count($i/author) div count($i)
    }</avg>
  }</item>
}</root>