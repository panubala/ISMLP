let $root := doc('../proceedings.xml')/root

return <root>{

for $proceedings in $root/proceedings
return <conferenceEdition>{
  <id>{$proceedings/ceid/text()}</id>,
  <cid>{$proceedings/cid/text()}</cid>,
  <pid>{$proceedings/id/text()}</pid>
}</conferenceEdition>

}</root>