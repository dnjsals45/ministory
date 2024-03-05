import React, { useRef } from 'react';
import { Editor } from '@toast-ui/react-editor';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';
import '@toast-ui/editor/dist/toastui-editor.css';
import 'tui-color-picker/dist/tui-color-picker.css';
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css';

interface MyEditorProps {
  onChangeContent: (newContent: string) => void;
}

const MyEditor: React.FC<MyEditorProps> = ({ onChangeContent }) => {
  const editorRef = useRef<Editor | null>(null);
  const toolbarItems = [
    ['heading', 'bold', 'italic', 'strike'],
    ['hr'],
    ['ul', 'ol', 'task'],
    ['table', 'link'],
    ['image'],
    ['code'],
    ['scrollSync'],
  ];

  const handleContentChange = () => {
    if (editorRef.current) {
      const editorIns = editorRef.current.getInstance();
      const contentHtml = editorIns.getHTML();
      onChangeContent(contentHtml); // 상위 컴포넌트로 에디터의 내용을 전달합니다.
    }
  };

  return (
    <>
      <Editor
        ref={editorRef}
        initialValue=""
        initialEditType="wysiwyg"
        hideModeSwitch={true}
        // previewStyle="vertical" // markdown 타입에서만 유효
        height="500px"
        theme={''}
        usageStatistics={false}
        toolbarItems={toolbarItems}
        plugins={[colorSyntax]}
        onChange={handleContentChange}
      />
    </>
  );
};

export default MyEditor;
