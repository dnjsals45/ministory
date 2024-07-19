import React, { useRef } from 'react'
import colorSyntax from '@toast-ui/editor-plugin-color-syntax'
import '@toast-ui/editor/dist/toastui-editor.css'
import 'tui-color-picker/dist/tui-color-picker.css'
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css'
import '@toast-ui/editor/dist/theme/toastui-editor-dark.css'
import { Editor } from '@toast-ui/react-editor'
import process from 'process'
import fieldTypes from 'rehype-citation/node/src/citation-js/plugin-bibtex/input/fieldTypes'
import title = fieldTypes.title
import { fetchWithAuthorization } from '@/components/hooks/CustomFetch'

export async function fetchUploadImage(
  blob,
  accessToken: string
): Promise<{ data: { imageUrl: string } }> {
  const formData = new FormData()
  formData.append('image', blob)

  const response = await fetch(
    process.env.NEXT_PUBLIC_BACKEND_URL + '/api/v1/contents/image-upload',
    {
      method: 'POST',
      body: formData,
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    }
  )

  return response.json()
}

export default function MyEditor({ onChangeContent, initialValue, accessToken }) {
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
      // const content = editorIns.getHTML()
      const content = editorIns.getMarkdown()
      onChangeContent(content)
    }
  }

  const onUploadImage = async (blob, callback) => {
    await fetchUploadImage(blob, accessToken).then((response) => {
      callback(response.data.imageUrl, blob.name)
    })
    return false
  }

  return (
    <>
      <Editor
        ref={editorRef}
        initialValue={initialValue}
        // initialEditType="wysiwyg"
        hideModeSwitch={true}
        initialEditType="markdown"
        previewStyle="vertical" // markdown 타입에서만 유효
        height="800px"
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
