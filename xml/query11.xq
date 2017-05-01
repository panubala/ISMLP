let $proceedings := doc('proceedings.xml')/root//*[cid/text() = 'CONPAR'],
$inproceedings := doc('inproceedings.xml')/root//*[pid/text() = $proceedings/id/text()]
return <root>{$inproceedings}</root>