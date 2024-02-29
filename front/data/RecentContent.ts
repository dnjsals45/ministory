interface Content {
  contentId: number;
  title: string;
  body: string;
  complete: boolean;
  views: number;
}

interface User {
  nickname: string;
}

export interface ContentItem {
  content: Content;
  user: User;
}
