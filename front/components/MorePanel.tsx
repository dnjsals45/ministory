import TempContentButton from '@/components/TempContentButton'
import TagSettingButton from '@/components/TagSettingButton'

export default function MorePanel({ setOpen }) {
  return (
    <div className="py-2">
      <TempContentButton setOpen={setOpen} />
      {/*<TagSettingButton />*/}
    </div>
  )
}
