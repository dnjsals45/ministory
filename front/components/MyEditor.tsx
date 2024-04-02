import React, { useRef } from 'react'
import colorSyntax from '@toast-ui/editor-plugin-color-syntax'
import '@toast-ui/editor/dist/toastui-editor.css'
import 'tui-color-picker/dist/tui-color-picker.css'
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css'
import '@toast-ui/editor/dist/theme/toastui-editor-dark.css'
import { Editor } from '@toast-ui/react-editor'
import process from 'process'

export async function fetchUploadImage(blob): Promise<{ data: { imageUrl: string } }> {
  const formData = new FormData()
  formData.append('image', blob)

  const response = await fetch(
    process.env.NEXT_PUBLIC_BACKEND_URL + '/api/v1/contents/image-upload',
    {
      method: 'POST',
      body: formData,
    }
  )
  return response.json()
}

export default function MyEditor({ onChangeContent }) {
  const editorRef = useRef<Editor | null>(null)
  const toolbarItems = [
    ['heading', 'bold', 'italic', 'strike'],
    ['hr'],
    ['ul', 'ol', 'task'],
    ['table', 'link'],
    ['image'],
    ['code'],
    ['scrollSync'],
  ]

  const handleContentChange = () => {
    if (editorRef.current) {
      const editorIns = editorRef.current.getInstance()
      const contentHtml = editorIns.getHTML()
      onChangeContent(contentHtml)
    }
  }

  const onUploadImage = async (blob, callback) => {
    await fetchUploadImage(blob).then((response) => {
      callback(response.data.imageUrl, blob.name)
    })
    return false
  }

  return (
    <>
      <Editor
        ref={editorRef}
        initialValue=""
        initialEditType="wysiwyg"
        hideModeSwitch={true}
        // previewStyle="vertical" // markdown 타입에서만 유효
        height="500px"
        usageStatistics={false}
        toolbarItems={toolbarItems}
        plugins={[colorSyntax]}
        onChange={handleContentChange}
        hooks={{
          addImageBlobHook: onUploadImage,
        }}
      />
    </>
  )
}
