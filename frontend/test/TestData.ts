import {CollectionModel, Item} from "@/types/CollectionModelTypes";

export class TestData {
    public static generateItem(id: number, message: string, isDone: boolean): Item {
        return {
            id: id,
            message: message,
            isDone: isDone,
            _links: {
                collection: {
                    href: "http://localhost:0/items"
                },
                delete: {
                    href: "http://localhost:0/delete_me"
                },
                setToDone: {
                    href: "http://localhost:0/i_am_done"
                },
                setToUndone: {
                    href: "http://localhost:0/i_am_undone"
                }
            }
        };
    }

    public static generateCollectionModel(items?: Item[]): CollectionModel {
        if (items) {
            return {
                _embedded: {
                    itemResponseList: items,
                },
                _links: {
                    self: {
                        href: "http://localhost:8082/items" // This one is hardcoded in the frontend
                    },
                    post: {
                        href: "http://localhost:0/post_item"
                    }
                }
            };
        } else {
            return {
                _links: {
                    self: {
                        href: "http://localhost:8082/items" // This one too FIX REDUNDANCY!
                    },
                    post: {
                        href: "http://localhost:0/post_item"
                    }
                }
            };
        }
    }

    public static pathFrom(href: string): string {
        const notPath: RegExp = /^.*:.*:[0-9]*/;
        href = href.replace(notPath, "");
        return href;
    }
}