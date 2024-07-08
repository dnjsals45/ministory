import React, { useState, useRef, useEffect } from 'react'
import MoreVertIcon from '@mui/icons-material/MoreVert'
import MorePanel from './MorePanel'

export default function MoreButton() {
  const [open, setOpen] = useState(false)
  const ref = useRef<HTMLDivElement>(null)

  const toggleOpen = () => {
    setOpen(!open)
  }

  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (ref.current && !ref.current.contains(event.target as Node)) {
        setOpen(false)
      }
    }

    document.addEventListener('mousedown', handleClickOutside)
    return () => {
      document.removeEventListener('mousedown', handleClickOutside)
    }
  }, [ref])

  return (
    <div className="relative inline-flex" ref={ref}>
      <button onClick={toggleOpen}>
        <MoreVertIcon />
      </button>
      {open && (
        <div className="absolute right-0 top-full z-10 mt-2 rounded-md bg-white shadow-md dark:bg-gray-800">
          <MorePanel setOpen={setOpen} />
        </div>
      )}
    </div>
  )
}
