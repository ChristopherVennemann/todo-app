export type Href = {
    href: string;
}

export type LinkCollection = {
    [key: string]: Href;
}

export type Item = {
    id: number;
    message: string;
    isDone: boolean;
    _links: LinkCollection;
}

export type CollectionModel = {
    _embedded?: {
        itemResponseList: Item[]
    }
    _links: LinkCollection;
}