import { useState } from 'react'
import MoreVertIcon from '@mui/icons-material/MoreVert'
import MorePanel from '@/components/MorePanel'

export default function MoreButton() {
  const [open, setOpen] = useState(false)

  const toggleOpen = () => {
    setOpen(!open)
  }

  return (
    <div className="relative inline-flex">
      <button onClick={toggleOpen}>
        <MoreVertIcon />
      </button>
      {open && (
        <div className="absolute right-0 top-full z-10 mt-2 rounded-md bg-white shadow-md dark:bg-gray-800">
          <MorePanel />
        </div>
      )}
    </div>
  )
}
