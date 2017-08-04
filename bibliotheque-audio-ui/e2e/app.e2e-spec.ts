import { BibliothequeAudioUiPage } from './app.po';

describe('bibliotheque-audio-ui App', () => {
  let page: BibliothequeAudioUiPage;

  beforeEach(() => {
    page = new BibliothequeAudioUiPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
