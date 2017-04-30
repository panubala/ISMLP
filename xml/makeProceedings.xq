let $root := doc('database.xml')/root
return <root>{

for $proceedings in $root//proceedings
return <proceedings>{
  <id>{data($proceedings/@key)}</id>,
  <title>{$proceedings/title/text()}</title>,
  <cid>{$proceedings/booktitle/text()}</cid>,
  <sid>{$proceedings/series/text()}</sid>,
  <publisher>{$proceedings/publisher/text()}</publisher>,
  <isbn>{$proceedings/isbn/text()}</isbn>,
  <year>{$proceedings/year/text()}</year>,
  for $editor in $proceedings//editor
  return <editor>{$editor/text()}</editor>
}</proceedings>

}</root>