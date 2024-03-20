import Image from './Image'
import Link from './Link'

const RecentPostCard = ({ data }) => {
  const href: string = '/blog/' + data.content.contentId
  const imgSrc = ''

  return (
    <div className="md max-h-full max-w-full p-4 md:w-1/3">
      <div
        className={`${
          imgSrc && 'h-full'
        }  overflow-hidden rounded-md border-2 border-gray-200 border-opacity-60 dark:border-gray-700`}
      >
        {imgSrc && (
          <Link href={href} aria-label={`Link to ${data.content.title}`}>
            <Image
              alt={data.content.title}
              src={imgSrc}
              className="object-cover object-center md:h-24 lg:h-32"
              width={362}
              height={204}
            />
          </Link>
        )}
        <div className="p-6">
          <h2 className="mb-3 line-clamp-2 min-h-[64px] overflow-ellipsis whitespace-normal break-words text-xl font-bold leading-8 tracking-tight">
            <Link href={href} aria-label={`Link to ${data.content.title}`}>
              {data.content.title}
            </Link>
          </h2>
          <p className="prose mb-3 line-clamp-4 min-h-[112px] max-w-none overflow-ellipsis whitespace-normal break-words text-gray-500 dark:text-gray-400">
            {data.content.body}
          </p>
          <p className="-mb-4 text-right text-xs leading-6 text-slate-400 dark:hover:text-primary-400">
            {data.content.createdAt.toString().split('T')[0]} | 조회수: {data.content.views}
          </p>
        </div>
      </div>
    </div>
  )
}

export default RecentPostCard
