interface Project {
  title: string
  description: string
  href?: string
  imgSrc?: string
}

const projectsData: Project[] = [
  {
    title: 'Webserv',
    description: 'C++을 이용한 I/O 멀티플렉싱 웹 개발',
    imgSrc: '/static/images/time-machine.jpg',
    href: 'https://github.com/dnjsals45/42Seoul-Webserv',
  },
  {
    title: 'Ministory',
    description: '1인 블로그 프로젝트',
    imgSrc: '/static/images/ministory.png',
    href: 'https://github.com/dnjsals45/ministory',
  },
]

export default projectsData
